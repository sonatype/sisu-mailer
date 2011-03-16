package org.sonatype.micromailer.imp;

import java.util.Enumeration;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.codehaus.plexus.velocity.VelocityComponent;
import org.slf4j.Logger;

@Singleton
@Named( "micromailer" )
public class MicromailerVelocityComponent
    implements VelocityComponent
{
    @Inject
    private Logger logger;

    private VelocityEngine engine;

    private Properties properties;

    public MicromailerVelocityComponent()
    {
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

}
