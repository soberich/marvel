
package org.openapitools.client.model


case class RecordCollectionDetailedView (
    _employeeId: Option[Long],
    _id: Option[Long],
    _projectId: Option[String],
    _records: Option[List[RecordDetailedView]],
    _version: Option[Integer],
    _yearMonth: Option[Integer]
)
object RecordCollectionDetailedView {
    def toStringBody(var_employeeId: Object, var_id: Object, var_projectId: Object, var_records: Object, var_version: Object, var_yearMonth: Object) =
        s"""
        | {
        | "employeeId":$var_employeeId,"id":$var_id,"projectId":$var_projectId,"records":$var_records,"version":$var_version,"yearMonth":$var_yearMonth
        | }
        """.stripMargin
}
