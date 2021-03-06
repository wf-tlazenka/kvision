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

import kotlinx.serialization.json.JSON
import pl.treksoft.jquery.JQueryAjaxSettings
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.utils.obj
import kotlin.js.Promise
import kotlin.js.undefined
import kotlin.js.JSON as NativeJSON

/**
 * An agent responsible for remote calls.
 */
open class CallAgent {

    private var counter = 1

    /**
     * Makes an JSON-RPC call to the remote server.
     * @param url an URL address
     * @param method a HTTP method
     * @param data data to be sent
     * @return a promise of the result
     */
    @Suppress("UnsafeCastFromDynamic")
    fun jsonRpcCall(
        url: String,
        data: List<String?> = listOf(),
        method: RpcHttpMethod = RpcHttpMethod.POST
    ): Promise<String> {
        val jsonRpcRequest = JsonRpcRequest(counter++, url, data)
        val jsonData = JSON.stringify(jsonRpcRequest)
        return Promise({ resolve, reject ->
            jQuery.ajax(url, obj {
                this.contentType = "application/json"
                this.data = jsonData
                this.method = method.name
                this.success =
                        { data: dynamic, _: Any, _: Any ->
                            if (data.id != jsonRpcRequest.id) {
                                reject(Exception("Invalid response ID"))
                            } else if (data.error != null) {
                                reject(Exception(data.error.toString()))
                            } else if (data.result != null) {
                                resolve(data.result)
                            } else {
                                reject(Exception("Invalid response"))
                            }
                        }
                this.error =
                        { xhr: JQueryXHR, _: String, errorText: String ->
                            val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                                xhr.responseJSON.toString()
                            } else {
                                errorText
                            }
                            reject(Exception(message))
                        }
            })
        })
    }

    /**
     * Makes a remote call to the remote server.
     * @param url an URL address
     * @param method a HTTP method
     * @param data data to be sent
     * @return a promise of the result
     */
    @Suppress("UnsafeCastFromDynamic")
    fun remoteCall(
        url: String,
        data: dynamic = null,
        method: HttpMethod = HttpMethod.GET,
        contentType: String = "application/json",
        beforeSend: ((JQueryXHR, JQueryAjaxSettings) -> Boolean)? = null
    ): Promise<dynamic> {
        return Promise({ resolve, reject ->
            jQuery.ajax(url, obj {
                this.contentType = contentType
                this.data = data
                this.method = method.name
                this.success =
                        { data: dynamic, _: Any, _: Any ->
                            resolve(data)
                        }
                this.error =
                        { xhr: JQueryXHR, _: String, errorText: String ->
                            val message = if (xhr.responseJSON != null && xhr.responseJSON != undefined) {
                                xhr.responseJSON.toString()
                            } else {
                                errorText
                            }
                            reject(Exception(message))
                        }
                this.beforeSend = beforeSend
            })
        })
    }
}
