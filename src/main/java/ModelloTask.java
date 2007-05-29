import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import org.apache.tools.ant.*;

public class ModelloTask extends Task {
    private String model;
    private String plugin;
    private String output;
    private String version;
    private boolean packageWithVersion;

    public void execute() throws BuildException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try
        {
            work();
        }
        catch( Exception e )
        {
            throw new BuildException( "Error while invoking Modello", e );
        }
        finally
        {
            Thread.currentThread().setContextClassLoader( cl );
        }
    }

    private void work() throws Exception {
        log( "Running the '" + plugin + "' Modello plugin using model file " + model + " for version " + version );

        URL[] urls = new URL[] {
            new URL( "file:/usr/share/java/libplexus-utils.jar" ),
            new URL( "file:/usr/share/java/plexus-classworlds.jar" ),
            new URL( "file:/usr/share/java/plexus-container-default.jar" ),
            new URL( "file:/usr/share/java/modello-core.jar" ),
            new URL( "file:/usr/share/java/modello-plugin-xml.jar" ),
            new URL( "file:/usr/share/java/modello-plugin-xpp3.jar" ),
        };

        ClassLoader cl = new URLClassLoader( urls );

        Thread.currentThread().setContextClassLoader( cl );

        String[] args = new String[]{
            new File( getProject().getBaseDir(), model ).getAbsolutePath(),
            plugin,
            output,
            version,
            Boolean.toString( packageWithVersion )};

        Class modelloCli = cl.loadClass( "org.codehaus.modello.ModelloCli" );
        Method main = modelloCli.getMethod( "main", new Class[] { String[].class } );
        main.invoke( null, new Object[] { args } );
    }

    public void setModel( String model ) {
        this.model = model;
    }

    public void setPlugin( String plugin ) {
        this.plugin = plugin;
    }

    public void setOutput( String output ) {
        this.output = output;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public void setPackageWithVersion( boolean packageWithVersion ) {
        this.packageWithVersion = packageWithVersion;
    }
}
