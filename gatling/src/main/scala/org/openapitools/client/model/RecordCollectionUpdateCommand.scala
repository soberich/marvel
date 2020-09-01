
package org.openapitools.client.model


case class RecordCollectionUpdateCommand (
    _id: Option[Long],
    _records: Option[List[RecordUpdateCommand]],
    _version: Option[Integer],
    _yearMonth: Option[Integer]
)
object RecordCollectionUpdateCommand {
    def toStringBody(var_id: Object, var_records: Object, var_version: Object, var_yearMonth: Object) =
        s"""
        | {
        | "id":$var_id,"records":$var_records,"version":$var_version,"yearMonth":$var_yearMonth
        | }
        """.stripMargin
}
