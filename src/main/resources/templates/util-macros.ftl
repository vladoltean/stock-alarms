<#macro alarmSaving>
    <#if alarmSaved??>
        <div class="alert alert-success" role="alert">
            Alarm saved succesfully!
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>

    <#if alarmError??>
        <div class="alert alert-danger" role="alert">
            Error: ${error!"Something went wrong!"}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
</#macro>