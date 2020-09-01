
package org.openapitools.client.model


case class FlowableProjectView (
    _name: Option[String],
    _version: Option[Integer]
)
object FlowableProjectView {
    def toStringBody(var_name: Object, var_version: Object) =
        s"""
        | {
        | "name":$var_name,"version":$var_version
        | }
        """.stripMargin
}
