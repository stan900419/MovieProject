package controller;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/apiEmployee")
public class EmployeeApp extends Application {
	@SuppressWarnings("unchecked")
	@Override
    public Set<Class<?>> getClasses(){
    	 return new HashSet<>(Arrays.asList(EmployeeController.class));
    }
}
