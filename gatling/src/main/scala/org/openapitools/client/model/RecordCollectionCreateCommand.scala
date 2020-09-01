
package org.openapitools.client.model


case class RecordCollectionCreateCommand (
    _employeeId: Option[Long],
    _projectId: Option[String],
    _records: Option[List[RecordCreateCommand]],
    _yearMonth: Option[Integer]
)
object RecordCollectionCreateCommand {
    def toStringBody(var_employeeId: Object, var_projectId: Object, var_records: Object, var_yearMonth: Object) =
        s"""
        | {
        | "employeeId":$var_employeeId,"projectId":$var_projectId,"records":$var_records,"yearMonth":$var_yearMonth
        | }
        """.stripMargin
}
