# sbt-orchid

An [sbt](https://www.scala-sbt.org/) plugin for the [Orchid static site generator](https://orchid.netlify.com/).

### Introduction

[Orchid](https://orchid.netlify.com/) is a JVM-hosted static-site generator. It's basic work is to take a bunch
and wide variety of source files and convert them into a static website.

### Basic Use

Following _sbt_-ish conventions, _sbt-orchid_ by default looks for source files in `src/main/orchid` of your project,
and delivers a site to `target/orchid`.

For information about what the source directory should contain, it's best to go through the [Orchid tutorials](https://orchid.netlify.com/wiki/learn) (source repository [here](https://github.com/JavaEden/OrchidTutorials)).

---

**Note: The gradle-project directory `src/orchid/resources` referred to in the tutorials becomes `src/main/orchid` (with _no_ "resources" directory) under this sbt plugin.**

If you don't like this choice, you can define any soure directory you'd like for your Orchid site via the setting `orchidSource`.

---

Once you have placed your files in `src/main/orchid`, the usual workflow will be via the task `orchidServe`, which builds the site
and starts an internal webserver on `localhost` to view it (on port 8080, so `http://localhost:8080`, by default).

```
sbt:orchid-play> orchidServe
Using the following modules: 
--------------------
 * com.eden.orchid.StandardModule

Auto-loaded modules: 
--------------------
 * com.eden.orchid.bsdoc.BsDocModule
 * com.eden.orchid.impl.compilers.markdown.FlexmarkModule
 * com.eden.orchid.impl.compilers.pebble.PebbleModule
 * com.eden.orchid.pages.PagesModule
 * com.eden.orchid.plugindocs.PluginDocsModule

Flag values: 
--------------------
-adminTheme: Default
-baseUrl: /
-defaultTemplateExtension: peb
-dest: /Users/swaldman/tmp/orchid-play/target/orchid
-dryDeploy: false
-environment: debug
-logLevel: VERBOSE
-port: 8080
-src: /Users/swaldman/tmp/orchid-play/src/main/orchid
-task: serve
-theme: BsDoc
-version: 0.1.0-SNAPSHOT

[INFO] Orchid: Running Orchid version 0.17.6, site version 0.1.0-SNAPSHOT in debug environment
[INFO] OrchidWebserver: Webserver Running at http://localhost:8080
[INFO] OrchidWebsocket: Websocket running at http://localhost:8081/
[INFO] TaskServiceImpl: Build Starting...
[INFO] GeneratorServiceImpl: Indexing [10000: assets]
[INFO] GeneratorServiceImpl: Indexing [1000: home]
[INFO] GeneratorServiceImpl: Indexing [1000: pages]
[INFO] GeneratorServiceImpl: Indexing [11: sitemap]
[INFO] GeneratorServiceImpl: Generating [10000: assets]
[INFO] GeneratorServiceImpl: Generating [1000: home]
[INFO] GeneratorServiceImpl: Generating [1000: pages]
[INFO] GeneratorServiceImpl: Generating [11: sitemap]

Build Metrics: 
┌───────┬────────────┬───────────────┬─────────────────┬───────────────────────────┬─────────────────────────────┐
│       │ Page Count │ Indexing Time │ Generation Time │ Mean Page Generation Time │ Median Page Generation Time │
├───────┼────────────┼───────────────┼─────────────────┼───────────────────────────┼─────────────────────────────┤
│ pages │     3      │     34ms      │      61ms       │           18ms            │            19ms             │
├───────┼────────────┼───────────────┼─────────────────┼───────────────────────────┼─────────────────────────────┤
│  home │     2      │     17ms      │      695ms      │           347ms           │            678ms            │
├───────┼────────────┼───────────────┼─────────────────┼───────────────────────────┼─────────────────────────────┤
│ TOTAL │          5 │         155ms │           780ms │                     150ms │                        19ms │
└───────┴────────────┴───────────────┴─────────────────┴───────────────────────────┴─────────────────────────────┘

Build Complete
Generated 5 pages in 1s 603ms

[WARN] Warnings:
[WARN] ModularList: 
[WARN]     - OrchidComponent type [recentPosts] could not be found (on page 'Orchid Starter' at http://localhost:8080)
[WARN]     - OrchidMenuFactory type [wikiSections] could not be found (on page 'Orchid Starter' at http://localhost:8080)
[WARN]     - OrchidMenuFactory type [taxonomy] could not be found (on page 'Orchid Starter' at http://localhost:8080)


Webserver Running at http://localhost:8080
Hit [CTRL-C] to stop the server and quit Orchid

```

To just ensure that the site gets built without pausing to browse with a webserver, use `orchidBuild` instead of `orchidServe`.

### Setup

As is common for _sbt_ plugins, you will usually want to install this plugin by adding it to `project/plugins.sbt` in your
repository.

However, adding the plugin alone is _not_ usually sufficient to get a workable installation. _Orchid_ is configured in large part
by dynamically loading a variety of themes and plugins, which you will want to add the the `libraryDependencies` _of your build, not your project_.

An easy, clean way to deal with that is just to add these extra dependencies in your build.sbt file.

Here is an example to get you started:

```
resolvers += Resolver.jcenterRepo // hosts Orchid and its components

addSbtPlugin("com.mchange" % "sbt-orchid" % "0.0.1")

/*
 *  Add desired Orchid components to the build
 */
val OrchidVersion = "0.17.6"

def orchidComponent( name : String ) = "io.github.javaeden.orchid" % name % OrchidVersion

/*
 *  The plugin includes OrchidCore already as a dependency,
 *  but explicitly specifying it endures version consistency.
 */
 
libraryDependencies += orchidComponent( "OrchidCore" )

/*
 *  Uncomment the components you desire
 */

/* Plugins -- see https://orchid.netlify.com/plugins */

// libraryDependencies += orchidComponent( "OrchidPages" )
// libraryDependencies += orchidComponent( "OrchidPosts" )
// libraryDependencies += orchidComponent( "OrchidPluginDocs" )

// libraryDependencies += orchidComponent( "OrchidAsciidoc" )
// libraryDependencies += orchidComponent( "OrchidAzure" )
// libraryDependencies += orchidComponent( "OrchidBible" )
// libraryDependencies += orchidComponent( "OrchidBitbucket" )
// libraryDependencies += orchidComponent( "OrchidChangelog" )
// libraryDependencies += orchidComponent( "OrchidDiagrams" )
// libraryDependencies += orchidComponent( "OrchidForms" )
// libraryDependencies += orchidComponent( "OrchidGithub" )
// libraryDependencies += orchidComponent( "OrchidGitlab" )
// libraryDependencies += orchidComponent( "OrchidGroovydoc" )
// libraryDependencies += orchidComponent( "OrchidJavadoc" )
// libraryDependencies += orchidComponent( "OrchidKSS" )
// libraryDependencies += orchidComponent( "OrchidKotlindoc" )
// libraryDependencies += orchidComponent( "OrchidNetlify" )
// libraryDependencies += orchidComponent( "OrchidNetlifyCMS" )
// libraryDependencies += orchidComponent( "OrchidPresentations" )
// libraryDependencies += orchidComponent( "OrchidSearch" )
// libraryDependencies += orchidComponent( "OrchidSwagger" )
// libraryDependencies += orchidComponent( "OrchidSwiftdoc" )
// libraryDependencies += orchidComponent( "OrchidSyntaxHighlighter" )
// libraryDependencies += orchidComponent( "OrchidTaxonomies" )
// libraryDependencies += orchidComponent( "OrchidWiki" )
// libraryDependencies += orchidComponent( "OrchidWritersBlocks" )

/* Themes -- see https://orchid.netlify.com/themes */
/*                                                 */
/* Don't forget to set 'orchidTheme' in build,sbt! */

// libraryDependencies += orchidComponent( "OrchidBsDoc" )
// libraryDependencies += orchidComponent( "OrchidCopper" )
// libraryDependencies += orchidComponent( "OrchidEditorial" )
// libraryDependencies += orchidComponent( "OrchidFutureImperfect" )

```



