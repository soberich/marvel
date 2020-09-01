
package org.openapitools.client.model


case class ProjectUpdateCommand (
    _name: Option[String],
    _version: Option[Integer]
)
object ProjectUpdateCommand {
    def toStringBody(var_name: Object, var_version: Object) =
        s"""
        | {
        | "name":$var_name,"version":$var_version
        | }
        """.stripMargin
}
