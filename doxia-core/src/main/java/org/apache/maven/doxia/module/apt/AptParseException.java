package org.apache.maven.doxia.module.apt;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.doxia.parser.ParseException;

public class AptParseException
    extends ParseException
{
    /**
     * @deprecated source isn't a safe place to get linenumbers from
     * @param message
     * @param source
     */
    public AptParseException( String message, AptSource source )
    {
        super( null, message, source.getName(), source.getLineNumber() );
    }

    /**
     * @deprecated source isn't a safe place to get linenumbers from
     * @param message
     * @param source
     * @param e
     */
    public AptParseException( String message, AptSource source, Exception e )
    {
        super( e, message, source.getName(), source.getLineNumber() );
    }


    public AptParseException( String message, String name, int lineNumber, Exception e)
    {
        super( e, message, name, lineNumber);
    }

    public AptParseException( String message, Exception e )
    {
        super( message, e );
    }


    public AptParseException( String message )
    {
        super( message );
    }

    public AptParseException( Exception e )
    {
        super( e );
    }
}
