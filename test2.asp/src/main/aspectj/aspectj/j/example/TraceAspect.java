package aspectj.j.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class TraceAspect {
	private static final Logger log = LoggerFactory.getLogger(TraceAspect.class);

	private static final String CSV_FILE_PATH = "execution_times.csv";
	
	public TraceAspect() {
		log.info("TraceAspect initialized");
	}

//    @Pointcut("execution(* aspectj.j.example..*(..)) && !within(aspectj.j.example.TraceAspect)")    
//    public void traceMethods() { 
//        // Pointcut definition
//    }

	// Define the pointcut specifically for insertManyItems in HeavyComp
	@Pointcut("execution(* aspectj.j.example.HeavyComp.insertManyItems(..))")
	public void insertManyItemsMethod() {
		// Pointcut definition for the specific method
	}

//    
//    // Define a pointcut expression to match the desired methods
//    @After("traceMethods()")
//    public void logAfter(JoinPoint joinPoint) {    	
//        log.info("Executed method: " + joinPoint.getSignature());
//    }

	// Profile execution time only for insertManyItems method
	@Around("insertManyItemsMethod()")
	public Object profileMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[] args = joinPoint.getArgs(); // This will give you an array of parameters

		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed();

		long end = System.currentTimeMillis();
		long executionTime = end - start;

		log.info("Executed method: {} with args: {} in {} ms", joinPoint.getSignature(), args, executionTime);
		saveExecutionDataToCSV(joinPoint.getSignature().toString(), args, executionTime);

		return result;
	}

	 private void saveExecutionDataToCSV(String signature, Object[] args, long executionTime) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
	            // Append header if file is empty
	            if (new java.io.File(CSV_FILE_PATH).length() == 0) {
	                writer.write("Method Signature,Arguments,Execution Time (ms)");
	                writer.newLine();
	            }

	            // Format arguments as a string
	            String argsFormatted = formatArguments(args);

	            // Write the method signature, arguments, and execution time
	            writer.write(String.format("%s,%s,%d", signature, argsFormatted, executionTime));
	            writer.newLine();
	        } catch (IOException e) {
	            log.error("Error writing to CSV file: {}", e.getMessage());
	        }
	    }

	    private String formatArguments(Object[] args) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < args.length; i++) {
	            sb.append(args[i]);
	            if (i < args.length - 1) {
	                sb.append("; "); // Separate multiple arguments with a semicolon
	            }
	        }
	        return sb.toString();
	    }
}
