/**
 * Copyright 2009-2010 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of the LGPL version 3.0:
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
 */

package com.threecrickets.scripturian.helper;

import javax.script.ScriptEngine;

import com.threecrickets.scripturian.Document;
import com.threecrickets.scripturian.ScriptletParsingHelper;
import com.threecrickets.scripturian.annotation.ScriptEnginePriorityExtensions;
import com.threecrickets.scripturian.annotation.ScriptEngines;

/**
 * An {@link ScriptletParsingHelper} that supports the JavaScript scripting
 * language as implemented by <a href="http://www.mozilla.org/rhino/">Rhino</a>.
 * 
 * @author Tal Liron
 */
@ScriptEngines(
{
	"rhino-nonjdk", "rhino", "js", "javascript", "JavaScript", "ecmascript", "ECMAScript"
})
@ScriptEnginePriorityExtensions(
{
	"js"
})
public class RhinoScriptletParsingHelper implements ScriptletParsingHelper
{
	//
	// ScriptletParsingHelper
	//

	public boolean isPrintOnEval()
	{
		return false;
	}

	public boolean isCompilable()
	{
		return true;
	}

	public String getScriptletHeader( Document document, ScriptEngine scriptEngine )
	{
		// Rhino's default implementation of print() is annoyingly a println().
		// This will fix it.
		return "print=function(s){context.writer.print(String(s));context.writer.flush();};";
	}

	public String getScriptletFooter( Document document, ScriptEngine scriptEngine )
	{
		return null;
	}

	public String getTextAsProgram( Document document, ScriptEngine scriptEngine, String content )
	{
		content = content.replaceAll( "\\n", "\\\\n" );
		content = content.replaceAll( "\\'", "\\\\'" );
		return "print('" + content + "');";
	}

	public String getExpressionAsProgram( Document document, ScriptEngine scriptEngine, String content )
	{
		return "print(" + content + ");";
	}

	public String getExpressionAsInclude( Document document, ScriptEngine scriptEngine, String content )
	{
		return document.getDocumentVariableName() + ".container.includeDocument(" + content + ");";
	}

	public String getInvocationAsProgram( Document document, ScriptEngine scriptEngine, String content )
	{
		return null;
	}
}
