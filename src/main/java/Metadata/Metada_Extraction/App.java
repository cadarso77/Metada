package Metadata.Metada_Extraction;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
    	File a = new File("/Users/Cadarso/Desktop/image/l.jpg");
        System.out.println( "Hello World!" );
        Metadata metadata = ImageMetadataReader.readMetadata(a);

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
            	 System.out.println("");
                System.out.format("[%s] - %s = %s",
                    directory.getName(), tag.getTagName(), tag.getDescription());
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
