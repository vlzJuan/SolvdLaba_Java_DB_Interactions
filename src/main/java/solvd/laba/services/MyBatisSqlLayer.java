package solvd.laba.services;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import solvd.laba.dao.CoreDAO;
import solvd.laba.mybatis.interfaces.StudentMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MyBatisSqlLayer {

    private final SqlSessionFactory sqlSessionFactory;
    private final ArrayList<Class<? extends CoreDAO<?,?>> > mapperInterfaces;

    public MyBatisSqlLayer() throws IOException {
        // Load MyBatis configuration and initialize SqlSessionFactory
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configStream);
        if (configStream != null) {
            configStream.close();
        }

        // Array of mapper interface classes
        mapperInterfaces = new ArrayList<>();
        mapperInterfaces.add(StudentMapper.class);
    }


    @SuppressWarnings("unchecked")
    public void execute(Scanner scanner) {
        boolean processMenu = true;
        while (processMenu) {
            System.out.println("Select a DAO and method to execute (or '-1' to exit):");

            int index = 0;
            for (Class<?> daoInterface : mapperInterfaces) {
                System.out.println("DAO " + index + ": " + daoInterface.getSimpleName());
                index++;
            }

            System.out.print("Enter DAO number: ");
            try {
                int daoNum = scanner.nextInt();
                scanner.nextLine();

                if(daoNum==-1){
                    processMenu = false;
                    System.out.println("DBInteraction menu exited. Have a good day!");
                }
                else if (daoNum < -1 || daoNum >= index) {
                    System.out.println("Invalid selection. Please try again.");
                }
                else{
                    currentMapper(scanner, (Class<? extends CoreDAO<Object, Object>>) mapperInterfaces.get(daoNum));
                }
            } catch (InputMismatchException exc) {
                System.out.println("Please enter an integer.");
            }
        }
    }


    @SuppressWarnings("unchecked")
    public <T, ID> void currentMapper(Scanner scanner, Class<? extends CoreDAO<T, ID>> mapperClass) {
        boolean processMenu = true;

        while (processMenu) {
            System.out.println("Select a method to use:");
            System.out.println("1 - Insert a row of data");
            System.out.println("2 - Read data by ID");
            System.out.println("3 - Update a row");
            System.out.println("4 - Delete a row by ID");
            System.out.println("5 - Retrieve all data");
            System.out.println("-1 - Exit.");

            try (SqlSession session = sqlSessionFactory.openSession()) {
                CoreDAO<T, ID> mapper = session.getMapper(mapperClass);

                // Retrieve the actual class of T at runtime
                Class<T> entityClass = getEntityClass(mapperClass);


                int option = scanner.nextInt();
                scanner.nextLine(); // consume newline
                ID id;
                String aux;

                switch (option) {
                    case 1:
                        //  This case nor the update properly creates the auxiliary T-type through Reflection.
                        //  The issue likely stems from the implementation of createObjectFromClass,
                        //  which is an offshoot of the ServiceClass.createObjectForDao method
                        //  to create an entity through reflection.
                        //  The method returns null pretty much always, and likely has to do with the fact
                        //  that, by trying to take the generic T-type from an interface (instead of
                        //  an instance of an object, as it was in the ServiceClass referenced method),
                        //  the type is not properly captures and no input prompts are displayed.
                        T newEntity = (T) ServiceLayer.createObjectForClass(scanner, entityClass);
                        System.out.println(newEntity.toString());
                        mapper.insert(newEntity);
                        session.commit();
                        break;
                    case 2:
                        System.out.println("Enter the key required to read: ");
                        aux = scanner.nextLine();
                        id = (ID) ServiceLayer.parseInput(aux, Integer.class);
                        T result = mapper.read(id);
                        if (result != null) {
                            System.out.println("The read entry is: \n" + result);
                        } else {
                            System.out.println("No entry found with that ID");
                        }
                        break;
                    case 3:
                        //  Has issues too
                        T updatedEntity = (T) ServiceLayer.createObjectForClass(scanner, entityClass);
                        mapper.update(updatedEntity);
                        session.commit();
                        break;
                    case 4:
                        System.out.println("Enter the key required to delete: ");
                        aux = scanner.nextLine();
                        id = (ID) ServiceLayer.parseInput(aux, Integer.class);
                        try {
                            mapper.delete(id);
                            session.commit();
                        } catch (Exception exc) {
                            System.out.println("Could not commit deletion. The selected row has a constraint attached.");
                        }
                        break;
                    case 5:
                        List<T> resultList = mapper.findAll();
                        System.out.println("The complete result set is:");
                        resultList.forEach(System.out::println);
                        break;
                    case -1:
                        processMenu = false;
                        System.out.println("MyBatis Interaction menu exited. Have a good day!");
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } catch (InputMismatchException exc) {
                System.out.println("Please enter an integer.");
                scanner.nextLine(); // Clear invalid input's newline
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static <T, ID> Class<T> getEntityClass(Class<? extends CoreDAO<T, ID>> mapperClass) {
        Class<T> entityClass = null;
        Type[] interfaces = mapperClass.getGenericInterfaces();

        for (Type interfaceType : interfaces) {
            if (interfaceType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) interfaceType;
                if (paramType.getRawType().equals(CoreDAO.class)) {
                    entityClass = (Class<T>) paramType.getActualTypeArguments()[0];
                    break;
                }
            }
        }

        if (entityClass == null) {
            throw new IllegalStateException("Could not determine entity class type.");
        }
        return entityClass;
    }


}
