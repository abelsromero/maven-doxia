package org.apache.maven.doxia.module.fml;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.parser.XhtmlBaseParser;
import org.apache.maven.doxia.sink.Sink;

import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Parse Fml questions and answers, these may contain arbitrary xdoc elements.
 *
 * @author ltheussl
 * @version $Id$
 * @since 1.0
 */
public class FmlContentParser
    extends XhtmlBaseParser
    implements FmlMarkup
{
    /** Empty elements don't write a closing tag. */
    private boolean isEmptyElement;

    /** {@inheritDoc} */
    protected void handleStartTag( XmlPullParser parser, Sink sink )
        throws XmlPullParserException, MacroExecutionException
    {
        isEmptyElement = parser.isEmptyElementTag();

        if ( isVerbatim() )
        {
            if ( parser.getName().equals( SOURCE_TAG.toString() ) )
            {
                verbatim();
            }

            sink.text( parser.getText() );
        }
        else if ( parser.getName().equals( QUESTION_TAG.toString() )
            || parser.getName().equals( ANSWER_TAG.toString() ) )
        {
            // ignore
            return;
        }
        else if ( parser.getName().equals( SOURCE_TAG.toString() ) )
        {
            verbatim();

            sink.verbatim( true );
        }
        else if ( !baseStartTag( parser, sink ) )
        {
            if ( isEmptyElement )
            {
                handleUnknown( parser, sink, TAG_TYPE_SIMPLE );
            }
            else
            {
                handleUnknown( parser, sink, TAG_TYPE_START );
            }

            if ( !isVerbatim() && getLog().isDebugEnabled() )
            {
                String position = "[" + parser.getLineNumber() + ":"
                    + parser.getColumnNumber() + "]";
                String tag = "<" + parser.getName() + ">";

                getLog().debug( "Unrecognized fml tag: " + tag + " at " + position );
            }
        }
    }

    /** {@inheritDoc} */
    protected void handleEndTag( XmlPullParser parser, Sink sink )
        throws XmlPullParserException, MacroExecutionException
    {
        if ( isVerbatim() )
        {
            if ( parser.getName().equals( SOURCE_TAG.toString() ) )
            {
                verbatim_();

                if ( isVerbatim() )
                {
                    sink.text( parser.getText() );
                }
                else
                {
                    sink.verbatim_();
                }
            }
            else
            {
                sink.text( parser.getText() );
            }
        }
        else if ( parser.getName().equals( QUESTION_TAG.toString() )
            || parser.getName().equals( ANSWER_TAG.toString() ) )
        {
            // ignore
            return;
        }
        else if ( parser.getName().equals( SOURCE_TAG.toString() ) )
        {
            verbatim_();

            sink.verbatim_();
        }
        else if ( !baseEndTag( parser, sink ) )
        {
            if ( !isEmptyElement )
            {
                handleUnknown( parser, sink, TAG_TYPE_END );
            }
        }

        isEmptyElement = false;
    }
}