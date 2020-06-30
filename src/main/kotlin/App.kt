class App {
    tailrec fun run() {
        println("YO!")
        Thread.sleep(5000)
        run()
    }
}

fun main() {
    val app = App()
    app.run()
}