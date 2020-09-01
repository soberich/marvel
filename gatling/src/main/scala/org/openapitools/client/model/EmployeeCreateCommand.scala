
package org.openapitools.client.model


case class EmployeeCreateCommand (
    _email: Option[String],
    _name: Option[String]
)
object EmployeeCreateCommand {
    def toStringBody(var_email: Object, var_name: Object) =
        s"""
        | {
        | "email":$var_email,"name":$var_name
        | }
        """.stripMargin
}
