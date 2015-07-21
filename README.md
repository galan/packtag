[![Build Status](https://img.shields.io/travis/galan/packtag.svg?style=flat)](https://travis-ci.org/galan/packtag)
[![Maven Central](https://img.shields.io/maven-central/v/de.galan.packtag/packtag-core.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/de.galan.packtag/packtag-core)
[![License](https://img.shields.io/github/license/galan/packtag.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0.html)

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

If you want to deepen your knowledge and learn about advanced techniques, I suggest reading "[pack:tag - a packed guide to website performance optimization](https://github.com/galan/packtag/raw/master/documentation/packtag%20-%20a%20packed%20guide%20to%20website%20performance%20optimization.pdf)".


# Integration

1. Add the maven dependency to your project:

        <dependency>
            <groupId>de.galan.packtag</groupId>
            <artifactId>packtag-core</artifactId>
            <version>3.13.0</version>
        </dependency>

2. Copy the `<servlet>` and `<servlet-mapping>` from the [web.xml](https://github.com/galan/packtag/blob/master/packtag-testsite/src/main/webapp/WEB-INF/web.xml) into your
/WEB-INF/web.xml


# Note
pack:tag is still supported, and still encouraged to be used if it makes sense for your project. But, no further development beyond bug fixes is expected. pack:tag is over 8 years old, and has been stable since then. Thank you to the community for your support of this project over the last years.

