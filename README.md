# pack:tag

A JSP Taglib for delivering minified, combined and gzip-compressed resources (JavaScript and CSS).

# Usage

To use the taglib in a JSP, you have to declare it first:

    <%@ taglib uri="https://github.com/d8bitr/packtag" prefix="pack" %>

(You can still use the old uri 'http://packtag.sf.net' for backward compatibility)

Now you can easily pack JavaScript by using the following tag:

    <pack:script src="/js/myJavaScriptFile.js"/>

Accordingly for Cascading Style Sheets:

    <pack:style src="/css/myCascadingStyleSheet.css"/>

You can enable and disable each tag individually by setting the attribute enabled to false, e.g.:

    <pack:script src="/js/myJavaScriptFile.js" enabled="false"/>

You can combine resources simply by listing them up:

    <pack:script>
      <src>/js/myJavaScriptFile.js</src>
      <src>/js/mySecondJavaScriptFile.js</src>
    </pack:script>

That's it.

If you want to deepen your knowledge and learn about advanced techniques, I suggest reading "[pack:tag - a packed guide to website performance optimization](https://github.com/d8bitr/packtag/raw/master/documentation/packtag%20-%20a%20packed%20guide%20to%20website%20performance%20optimization.pdf)".


# Integration

## Maven (preferred)
1. Use the [maven repository and artifact](https://github.com/d8bitr/maven-repository) on github.
2. Copy the `<servlet>` and `<servlet-mapping>` from the packtag/files/web.xml into your
/WEB-INF/web.xml

## Manual

1. Download the pack:tag archive from https://github.com/d8bitr/packtag/releases and unpack
the packtag-x.y.zip file to e.g. packtag
2. Copy packtag/files/packtag-x.y.jar into your web-applications WEB-INF/lib directory
3. Copy the `<servlet>` and `<servlet-mapping>` from the packtag/files/web.xml into your
/WEB-INF/web.xml
