
package org.openapitools.client.model

import java.time.LocalDate

case class RecordCreateCommand (
    _date: Option[LocalDate],
    _desc: Option[String],
    _hoursSubmitted: Option[String],
    _recordCollectionId: Option[Long],
    _type: Option[RecordType]
)
object RecordCreateCommand {
    def toStringBody(var_date: Object, var_desc: Object, var_hoursSubmitted: Object, var_recordCollectionId: Object, var_type: Object) =
        s"""
        | {
        | "date":$var_date,"desc":$var_desc,"hoursSubmitted":$var_hoursSubmitted,"recordCollectionId":$var_recordCollectionId,"type":$var_type
        | }
        """.stripMargin
}
