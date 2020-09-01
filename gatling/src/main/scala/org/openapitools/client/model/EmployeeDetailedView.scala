
package org.openapitools.client.model


case class EmployeeDetailedView (
    _email: Option[String],
    _id: Option[Long],
    _name: Option[String],
    _version: Option[Integer]
)
object EmployeeDetailedView {
    def toStringBody(var_email: Object, var_id: Object, var_name: Object, var_version: Object) =
        s"""
        | {
        | "email":$var_email,"id":$var_id,"name":$var_name,"version":$var_version
        | }
        """.stripMargin
}
