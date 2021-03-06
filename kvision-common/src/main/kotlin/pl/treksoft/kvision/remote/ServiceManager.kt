/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.remote

import kotlinx.coroutines.experimental.Deferred

enum class RpcHttpMethod {
    POST,
    PUT,
    DELETE,
    OPTIONS
}

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS
}

/**
 * Multiplatform service manager.
 */
expect open class ServiceManager<out T>(service: T? = null) {
    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected inline fun <reified RET> bind(
        route: String,
        noinline function: T.(Request?) -> Deferred<RET>,
        method: RpcHttpMethod = RpcHttpMethod.POST,
        prefix: String = "/"
    )

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected inline fun <reified PAR, reified RET> bind(
        route: String,
        noinline function: T.(PAR, Request?) -> Deferred<RET>,
        method: RpcHttpMethod = RpcHttpMethod.POST,
        prefix: String = "/"
    )

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected inline fun <reified PAR1, reified PAR2, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<RET>,
        method: RpcHttpMethod = RpcHttpMethod.POST,
        prefix: String = "/"
    )

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<RET>,
        method: RpcHttpMethod = RpcHttpMethod.POST,
        prefix: String = "/"
    )

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<RET>,
        method: RpcHttpMethod = RpcHttpMethod.POST,
        prefix: String = "/"
    )

    /**
     * Binds a given route with a function of the receiver.
     * @param route a route
     * @param function a function of the receiver
     * @param method a HTTP method
     * @param prefix an URL address prefix
     */
    protected inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5, reified RET> bind(
        route: String,
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<RET>,
        method: RpcHttpMethod = RpcHttpMethod.POST,
        prefix: String = "/"
    )

    /**
     * Applies all defined routes to the given server.
     * @param k a Jooby server
     */
    fun applyRoutes(k: JoobyServer)

    /**
     * Returns the map of defined paths.
     */
    fun getCalls(): Map<String, Pair<String, RpcHttpMethod>>
}
