
package org.openapitools.client.model


case class ProjectCreateCommand (
    _name: Option[String]
)
object ProjectCreateCommand {
    def toStringBody(var_name: Object) =
        s"""
        | {
        | "name":$var_name
        | }
        """.stripMargin
}
