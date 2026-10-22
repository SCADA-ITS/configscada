# mango-core

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.revenga.rits.back</groupId>
    <artifactId>mango-core</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.revenga.rits.back:mango-core:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/mango-core-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

```

## Documentation for API Endpoints

All URIs are relative to *https://localhost:8080/rest/v1*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------


## Documentation for Models

 - [ASTNode](docs/ASTNode.md)
 - [AbstractDataSourceModel](docs/AbstractDataSourceModel.md)
 - [AbstractDataSourceModelobject](docs/AbstractDataSourceModelobject.md)
 - [AbstractEventHandlerModel](docs/AbstractEventHandlerModel.md)
 - [AbstractEventHandlerModelAbstractEventHandlerVOobject](docs/AbstractEventHandlerModelAbstractEventHandlerVOobject.md)
 - [AbstractEventHandlerModelobject](docs/AbstractEventHandlerModelobject.md)
 - [AbstractEventHandlerVOobject](docs/AbstractEventHandlerVOobject.md)
 - [AbstractPointEventDetectorVOobject](docs/AbstractPointEventDetectorVOobject.md)
 - [AngularJSModuleDefinitionGroupModel](docs/AngularJSModuleDefinitionGroupModel.md)
 - [AtomicInteger](docs/AtomicInteger.md)
 - [AtomicIntegerMonitor](docs/AtomicIntegerMonitor.md)
 - [AuditEventDao](docs/AuditEventDao.md)
 - [AuditEventInstanceModel](docs/AuditEventInstanceModel.md)
 - [AuditEventInstanceVO](docs/AuditEventInstanceVO.md)
 - [BackgroundProcessingQueueCounts](docs/BackgroundProcessingQueueCounts.md)
 - [BackgroundProcessingRejectedTaskStats](docs/BackgroundProcessingRejectedTaskStats.md)
 - [BackgroundProcessingRunningStats](docs/BackgroundProcessingRunningStats.md)
 - [BacnetDiscoveryObjectRequestBean](docs/BacnetDiscoveryObjectRequestBean.md)
 - [BaseChartRendererModelobject](docs/BaseChartRendererModelobject.md)
 - [BaseTextRendererModelobject](docs/BaseTextRendererModelobject.md)
 - [Character](docs/Character.md)
 - [ChartRenderer](docs/ChartRenderer.md)
 - [CredentialsModel](docs/CredentialsModel.md)
 - [DataExportDefinition](docs/DataExportDefinition.md)
 - [DataPointModel](docs/DataPointModel.md)
 - [DataPointSummary](docs/DataPointSummary.md)
 - [DataPointSummaryModel](docs/DataPointSummaryModel.md)
 - [DataPointVO](docs/DataPointVO.md)
 - [DataSourceDefinition](docs/DataSourceDefinition.md)
 - [DataSourceVOobject](docs/DataSourceVOobject.md)
 - [DataValue](docs/DataValue.md)
 - [DateTimeZone](docs/DateTimeZone.md)
 - [DemoModel](docs/DemoModel.md)
 - [Dimension](docs/Dimension.md)
 - [DiskInfoModel](docs/DiskInfoModel.md)
 - [Element](docs/Element.md)
 - [EmailRecipientModelobject](docs/EmailRecipientModelobject.md)
 - [EmailTemplateModel](docs/EmailTemplateModel.md)
 - [EventAcknowledgeQueryStream](docs/EventAcknowledgeQueryStream.md)
 - [EventDetectorDefinitionobject](docs/EventDetectorDefinitionobject.md)
 - [EventExportDefinition](docs/EventExportDefinition.md)
 - [EventHandlerDefinitionobject](docs/EventHandlerDefinitionobject.md)
 - [EventHandlerRTobject](docs/EventHandlerRTobject.md)
 - [EventInstanceModel](docs/EventInstanceModel.md)
 - [EventInstanceVO](docs/EventInstanceVO.md)
 - [EventLevelSummaryModel](docs/EventLevelSummaryModel.md)
 - [EventRange](docs/EventRange.md)
 - [EventRangeModel](docs/EventRangeModel.md)
 - [EventType](docs/EventType.md)
 - [EventTypeInfo](docs/EventTypeInfo.md)
 - [EventTypeModel](docs/EventTypeModel.md)
 - [EventTypeVO](docs/EventTypeVO.md)
 - [ExcelReportModel](docs/ExcelReportModel.md)
 - [ExcelReportTemplateModel](docs/ExcelReportTemplateModel.md)
 - [ExcelReportTemplateVO](docs/ExcelReportTemplateVO.md)
 - [ExcelReportTimeSeries](docs/ExcelReportTimeSeries.md)
 - [ExcelReportVO](docs/ExcelReportVO.md)
 - [ExportCodes](docs/ExportCodes.md)
 - [FileModel](docs/FileModel.md)
 - [FileSystemResource](docs/FileSystemResource.md)
 - [FilterProvider](docs/FilterProvider.md)
 - [GrantedAuthority](docs/GrantedAuthority.md)
 - [HelpModel](docs/HelpModel.md)
 - [ImplDefinition](docs/ImplDefinition.md)
 - [ImportTask](docs/ImportTask.md)
 - [InputStream](docs/InputStream.md)
 - [JsonData](docs/JsonData.md)
 - [JsonNode](docs/JsonNode.md)
 - [JsonStreamPointHierarchyModel](docs/JsonStreamPointHierarchyModel.md)
 - [LocalDeviceConfig](docs/LocalDeviceConfig.md)
 - [Locale](docs/Locale.md)
 - [LockInfo](docs/LockInfo.md)
 - [LoggingPropertiesModel](docs/LoggingPropertiesModel.md)
 - [MailingListModel](docs/MailingListModel.md)
 - [MappingJacksonValue](docs/MappingJacksonValue.md)
 - [MapstringListPointValueTime](docs/MapstringListPointValueTime.md)
 - [Mapstringstring](docs/Mapstringstring.md)
 - [ModbusIpConfigurationBean](docs/ModbusIpConfigurationBean.md)
 - [ModbusIpReadLocatorBean](docs/ModbusIpReadLocatorBean.md)
 - [ModbusIpReadRequest](docs/ModbusIpReadRequest.md)
 - [ModbusIpWriteRegistersBean](docs/ModbusIpWriteRegistersBean.md)
 - [ModbusIpWriteRequest](docs/ModbusIpWriteRequest.md)
 - [ModbusPointLocatorBean](docs/ModbusPointLocatorBean.md)
 - [ModbusReadResponse](docs/ModbusReadResponse.md)
 - [ModbusScanResultModel](docs/ModbusScanResultModel.md)
 - [ModbusSerialConfigurationBean](docs/ModbusSerialConfigurationBean.md)
 - [ModbusSerialReadLocatorBean](docs/ModbusSerialReadLocatorBean.md)
 - [ModbusSerialReadRequest](docs/ModbusSerialReadRequest.md)
 - [ModbusSerialWriteRegistersBean](docs/ModbusSerialWriteRegistersBean.md)
 - [ModbusSerialWriteRequest](docs/ModbusSerialWriteRequest.md)
 - [Module](docs/Module.md)
 - [ModuleInfo](docs/ModuleInfo.md)
 - [ModuleModel](docs/ModuleModel.md)
 - [ModuleQueryExplainModel](docs/ModuleQueryExplainModel.md)
 - [ModuleQueryModel](docs/ModuleQueryModel.md)
 - [ModuleUpgradeModel](docs/ModuleUpgradeModel.md)
 - [ModuleUpgradesModel](docs/ModuleUpgradesModel.md)
 - [ObjectStreamMapstringListPointValueTime](docs/ObjectStreamMapstringListPointValueTime.md)
 - [OrderedTaskInfo](docs/OrderedTaskInfo.md)
 - [OutputStream](docs/OutputStream.md)
 - [PageQueryResultModelEmailTemplateModel](docs/PageQueryResultModelEmailTemplateModel.md)
 - [PageQueryResultModelTimezoneModel](docs/PageQueryResultModelTimezoneModel.md)
 - [PageQueryStreamAuditEventInstanceVOAuditEventInstanceModelAuditEventDao](docs/PageQueryStreamAuditEventInstanceVOAuditEventInstanceModelAuditEventDao.md)
 - [Permission](docs/Permission.md)
 - [PermissionDetails](docs/PermissionDetails.md)
 - [PointHierarchyModel](docs/PointHierarchyModel.md)
 - [PointHistoryCount](docs/PointHistoryCount.md)
 - [PointLocatorModelobject](docs/PointLocatorModelobject.md)
 - [PointLocatorVOobject](docs/PointLocatorVOobject.md)
 - [PointRange](docs/PointRange.md)
 - [PointRangeModel](docs/PointRangeModel.md)
 - [PointValueTime](docs/PointValueTime.md)
 - [PointValueTimeModel](docs/PointValueTimeModel.md)
 - [PointValuesRequestModel](docs/PointValuesRequestModel.md)
 - [ProcessMessage](docs/ProcessMessage.md)
 - [ProcessResult](docs/ProcessResult.md)
 - [PublishedPointVO](docs/PublishedPointVO.md)
 - [PublisherDefinition](docs/PublisherDefinition.md)
 - [PublisherVOPublishedPointVO](docs/PublisherVOPublishedPointVO.md)
 - [PurgeSettings](docs/PurgeSettings.md)
 - [QueryArrayStreamDataPointVO](docs/QueryArrayStreamDataPointVO.md)
 - [QueryArrayStreamEventInstanceVO](docs/QueryArrayStreamEventInstanceVO.md)
 - [QueryArrayStreamPointValueTimeModel](docs/QueryArrayStreamPointValueTimeModel.md)
 - [QueryArrayStreamobject](docs/QueryArrayStreamobject.md)
 - [QueryAttribute](docs/QueryAttribute.md)
 - [QueryDataPageStreamAbstractEventHandlerVOobject](docs/QueryDataPageStreamAbstractEventHandlerVOobject.md)
 - [QueryDataPageStreamDataPointVO](docs/QueryDataPageStreamDataPointVO.md)
 - [QueryDataPageStreamDataSourceVOobject](docs/QueryDataPageStreamDataSourceVOobject.md)
 - [QueryDataPageStreamEventInstanceVO](docs/QueryDataPageStreamEventInstanceVO.md)
 - [QueryDataPageStreamExcelReportTemplateVO](docs/QueryDataPageStreamExcelReportTemplateVO.md)
 - [QueryDataPageStreamExcelReportVO](docs/QueryDataPageStreamExcelReportVO.md)
 - [QueryDataPageStreamUser](docs/QueryDataPageStreamUser.md)
 - [QueryDataPageStreamUserCommentVO](docs/QueryDataPageStreamUserCommentVO.md)
 - [QueryDataPageStreamWatchListVO](docs/QueryDataPageStreamWatchListVO.md)
 - [QueryStreamUserCommentVOUserCommentModelUserCommentDao](docs/QueryStreamUserCommentVOUserCommentModelUserCommentDao.md)
 - [RealTimeModel](docs/RealTimeModel.md)
 - [RecentPointValueTimeModel](docs/RecentPointValueTimeModel.md)
 - [RecipientListEntryBean](docs/RecipientListEntryBean.md)
 - [RejectedTaskStats](docs/RejectedTaskStats.md)
 - [RelatedHelpItemModel](docs/RelatedHelpItemModel.md)
 - [Resource](docs/Resource.md)
 - [ResponseEntity](docs/ResponseEntity.md)
 - [RestValidationMessage](docs/RestValidationMessage.md)
 - [RowMapperAuditEventInstanceVO](docs/RowMapperAuditEventInstanceVO.md)
 - [RowMapperUserCommentVO](docs/RowMapperUserCommentVO.md)
 - [SessionInformation](docs/SessionInformation.md)
 - [StackTraceElement](docs/StackTraceElement.md)
 - [StatisticsStream](docs/StatisticsStream.md)
 - [StreamedArray](docs/StreamedArray.md)
 - [SystemInfoModel](docs/SystemInfoModel.md)
 - [TableModel](docs/TableModel.md)
 - [TextRenderer](docs/TextRenderer.md)
 - [ThreadModel](docs/ThreadModel.md)
 - [ThreadPoolSettingsModel](docs/ThreadPoolSettingsModel.md)
 - [TimePeriod](docs/TimePeriod.md)
 - [TimePeriodModel](docs/TimePeriodModel.md)
 - [TimeRangePointValuesRequestModel](docs/TimeRangePointValuesRequestModel.md)
 - [TimeSeriesModel](docs/TimeSeriesModel.md)
 - [TimeZone](docs/TimeZone.md)
 - [TimezoneModel](docs/TimezoneModel.md)
 - [TranslatableMessageModel](docs/TranslatableMessageModel.md)
 - [Translations](docs/Translations.md)
 - [TranslationsModel](docs/TranslationsModel.md)
 - [URI](docs/URI.md)
 - [URL](docs/URL.md)
 - [UnitConverter](docs/UnitConverter.md)
 - [Unitobject](docs/Unitobject.md)
 - [UpdateLicensePayloadModel](docs/UpdateLicensePayloadModel.md)
 - [UpgradeStatusModel](docs/UpgradeStatusModel.md)
 - [User](docs/User.md)
 - [UserAccessModel](docs/UserAccessModel.md)
 - [UserCommentDao](docs/UserCommentDao.md)
 - [UserCommentModel](docs/UserCommentModel.md)
 - [UserCommentVO](docs/UserCommentVO.md)
 - [ValueMonitorobject](docs/ValueMonitorobject.md)
 - [Version](docs/Version.md)
 - [WatchListDataPointModel](docs/WatchListDataPointModel.md)
 - [WatchListModel](docs/WatchListModel.md)
 - [WatchListParameter](docs/WatchListParameter.md)
 - [WatchListPointsQueryDataPageStream](docs/WatchListPointsQueryDataPageStream.md)
 - [WatchListSummaryModel](docs/WatchListSummaryModel.md)
 - [WatchListVO](docs/WatchListVO.md)
 - [WorkItemModel](docs/WorkItemModel.md)
 - [XidPointValueTimeModel](docs/XidPointValueTimeModel.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### Mango Token

- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author


