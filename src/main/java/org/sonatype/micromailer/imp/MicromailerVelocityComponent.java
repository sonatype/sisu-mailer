package org.sonatype.micromailer.imp;

import java.util.Enumeration;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogSystem;
import org.codehaus.plexus.velocity.VelocityComponent;
import org.slf4j.Logger;

@Singleton
@Named( "micromailer" )
public class MicromailerVelocityComponent
    implements VelocityComponent, LogSystem
{
    private Logger logger;

    private VelocityEngine engine;

    private Properties properties;

    @Inject
    public MicromailerVelocityComponent( Logger logger )
    {
        this.logger = logger;

        engine = new VelocityEngine();

        // avoid "unable to find resource 'VM_global_library.vm' in any resource loader."
        engine.setProperty( "velocimacro.library", "" );

        engine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, this );

        if ( properties != null )
        {
            for ( Enumeration e = properties.propertyNames(); e.hasMoreElements(); )
            {
                String key = e.nextElement().toString();

                String value = properties.getProperty( key );

                engine.setProperty( key, value );

                logger.debug( "Setting property: " + key + " => '" + value + "'." );
            }
        }

        try
        {
            engine.init();
        }
        catch ( Exception e )
        {
            logger.error( "Cannot start the velocity engine: ", e );
        }
    }

    public VelocityEngine getEngine()
    {
        return engine;
    }

    public void init( RuntimeServices runtimeServices )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    public void logVelocityMessage( int level, String message )
    {
        switch ( level )
        {
            case LogSystem.WARN_ID:
                logger.warn( message );
                break;
            case LogSystem.INFO_ID:
                // velocity info messages are too verbose, just consider them as debug messages...
                logger.debug( message );
                break;
            case LogSystem.DEBUG_ID:
                logger.debug( message );
                break;
            case LogSystem.ERROR_ID:
                logger.error( message );
                break;
            default:
                logger.debug( message );
                break;
        }

    }

}
