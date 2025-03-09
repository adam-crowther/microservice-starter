import static ratpack.groovy.Groovy.ratpack

ratpack {
  serverConfig {
    port %PORT%
  }
  handlers {
    get("") {
      redirect 301, "/index.html"
    }
    get("/") {
      redirect 301, "/index.html"
    }
    files { files -> files.dir("/") }
  }
}