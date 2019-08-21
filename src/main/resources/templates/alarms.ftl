<#import "/spring.ftl" as spring />
<#import "layout.ftl" as layout>

<@layout.myLayout>

    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Symbol</th>
            <th scope="col">Initial Price</th>
            <th scope="col">Current Price</th>
            <th scope="col">Variance</th>
            <th scope="col">Target variance</th>
            <th scope="col">Target price</th>

        </tr>
        </thead>
        <tbody>
        <#list alarms as alarm>
            <tr>
                <th scope="row">${alarm.stock.symbol!'N/A'}</th>
                <td>${alarm.referencePrice!'N/A'}</td>
                <td>${alarm.stock.price!'N/A'}</td>
                <td>${alarm.variance!'N/A'}</td>
                <td>${alarm.rule!'N/A'}</td>
                <td>${alarm.alarmPrice!'N/A'}</td>
            </tr>
        </#list>
        </tbody>
    </table>

</@layout.myLayout>
