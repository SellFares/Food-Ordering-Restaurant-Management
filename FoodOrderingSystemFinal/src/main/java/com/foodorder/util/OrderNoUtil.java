package com.foodorder.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoUtil {
	
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private static final Random RANDOM = new Random();

    public static String generate() {
    	
    	
        String timePart = FORMAT.format(new Date());
        
        
        int randomPart = 1000 + RANDOM.nextInt(9000); 
        
        
        return timePart + randomPart;
    }
}
