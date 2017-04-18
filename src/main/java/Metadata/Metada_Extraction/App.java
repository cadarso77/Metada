package Metadata.Metada_Extraction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ImageProcessingException, IOException
    {
    	File a = new File("/Volumes/EIT DOCS/mara2/6.JPG");
        Metadata metadata = ImageMetadataReader.readMetadata(a);

        boolean East = false;
        boolean South = false;
        
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
            	 
            	if(tag.getTagName() == "Date/Time"){
           		 
           		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss", Locale.ENGLISH);
           		 LocalDateTime date = LocalDateTime.parse(tag.getDescription(), formatter);
           		 int day = date.getDayOfMonth();
           		 String month = date.getMonth().toString();
           		 int year = date.getYear();
           		 int hour = date.getHour();
           		 int min = date.getMinute();
           		 int sec = date.getSecond();
           		 System.out.println();
           		 System.out.println(day + "-" + month + "-" + year + " " + hour + ":" + min + ":" + sec);
           		 
           		 
           	 	}
            	if(tag.getTagName() == "GPS Longitude"){
            		double deg = Double.parseDouble(tag.getDescription().split("°")[0]);
            		double min = Double.parseDouble(tag.getDescription().split("°")[1].split("'")[0].trim());
            		double sec = Double.parseDouble(tag.getDescription().split("°")[1].split("'")[1].split("\"")[0].trim());
            		double totalLong = Math.abs(deg) + Math.abs(min)/60 + Math.abs(sec)/3600;
            		if(deg<0){
            			totalLong = 0-totalLong;
            		}
            		if(East)
            			totalLong = 0-totalLong;
            		System.out.println(totalLong);
            	}
            	
            	if(tag.getTagName() == "GPS Latitude"){
            		double deg = Double.parseDouble(tag.getDescription().split("°")[0]);
            		double min = Double.parseDouble(tag.getDescription().split("°")[1].split("'")[0].trim());
            		double sec = Double.parseDouble(tag.getDescription().split("°")[1].split("'")[1].split("\"")[0].trim());
            		double totalLat = Math.abs(deg) + Math.abs(min)/60 + Math.abs(sec)/3600;
            		if(deg<0){
            			totalLat = 0-totalLat;
            		}
            		if(South)
            			totalLat = 0-totalLat;
            		System.out.println();
            		System.out.println(totalLat);
            	}
            	
            	if( tag.getTagName() == "GPS Latitude Ref" && tag.getDescription().equals("S")){
            		South = true;
            	}
            	 
            	if( tag.getTagName() == "GPS Longitude Ref" && tag.getDescription().equals("E")){
            		East = true;
            	}
            	
            	 System.out.println();
            	 System.out.format("[%s] -- %s = %s",directory.getName(), tag.getTagName(), tag.getDescription());
            }
            
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        //Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        //System.out.println("Date:");
        //System.out.println(date.toString());
    }
}
