
package org.openapitools.client.model

import java.time.LocalDate

case class RecordUpdateCommand (
    _date: Option[LocalDate],
    _desc: Option[String],
    _hoursSubmitted: Option[String],
    _recordCollectionId: Option[Long],
    _type: Option[RecordType],
    _version: Option[Integer]
)
object RecordUpdateCommand {
    def toStringBody(var_date: Object, var_desc: Object, var_hoursSubmitted: Object, var_recordCollectionId: Object, var_type: Object, var_version: Object) =
        s"""
        | {
        | "date":$var_date,"desc":$var_desc,"hoursSubmitted":$var_hoursSubmitted,"recordCollectionId":$var_recordCollectionId,"type":$var_type,"version":$var_version
        | }
        """.stripMargin
}
