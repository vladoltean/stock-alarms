<#import "/spring.ftl" as spring />

<#import "layout.ftl" as layout>

<@layout.myLayout>

    <#if alarmSaved??>
        <div class="alert alert-success" role="alert">
            Alarm saved succesfully!
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>

    <#if stockSaved??>
        <div class="alert alert-success" role="alert">
            Started monitoring stock! Now you can set alarms on it.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>

        <form id="add-stock-to-monitor-form" method="post" action="stocks/monitor">
            <div class="input-group">
                <input id="monitor-stock-name" class="form-control" type="text" name="name" autocomplete="off"
                       placeholder="Search for stocks to monitor...">
                <div class="input-group-append">
                    <button type="submit" form="add-stock-to-monitor-form" class="btn btn-primary">Monitor Stock</button>
                </div>

            </div>
            <input id="monitor-stock-symbol-hidden" type="hidden" name="symbol">
        </form>

    <br>

    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Symbol</th>
            <th scope="col">Company name</th>
            <th scope="col">Price</th>
            <th scope="col">Change</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list stocks as stock>
            <#if stock.symbol??>
                <tr>
                    <th scope="row">${stock.symbol!'N/A'}</th>
                    <td>${stock.companyName!'N/A'}</td>
                    <td>$${stock.price!'N/A'}</td>
                    <td>${stock.changePercent!'N/A'}</td>
                    <td>
                        <#assign changePercentNumber = stock.changePercent?remove_ending("%")?number>
                        <#if changePercentNumber gt 0>
                            <span class="glyphicon glyphicon-arrow-up" aria-hidden="true" style="color: green"></span>
                        <#elseif changePercentNumber == 0>
                            ---
                        <#else>
                            <span class="glyphicon glyphicon-arrow-down" aria-hidden="true" style="color:red"></span>
                        </#if>

                    </td>
                    <td>
                        <button type="button" class="btn btn-info" data-toggle="modal"
                                data-target="#addAlarmModal" data-symbol="${stock.symbol}">
                            Add alarm
                        </button>
                    </td>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>


    <!-- Modal -->
    <div class="modal fade" id="addAlarmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add new alarm</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="add-alarm-form" method="POST" action="alarms">
                        <#--                        TODO: Add tooltip and validation to rule-->
                        <input id="stockSymbol" class="form-control" type="text" placeholder="Stock Symbol" name="stockSymbol">
                        <input class="form-control" type="text" placeholder="Rule" name="rule">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" form="add-alarm-form" class="btn btn-primary">Save Alarm</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        $('#addAlarmModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var symbol = button.data('symbol'); // Extract info from data-* attributes
            // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
            // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
            var modal = $(this);
            modal.find('.modal-title').text('Add alarm for ' + symbol);
            modal.find('#stockSymbol').val(symbol);
        })

        $('#monitor-stock-name').typeahead({
            ajax: '/test/search',
            valueField: 'symbol',
            displayField: 'name',
            onSelect: function (arg) {
                $('#monitor-stock-symbol-hidden').val(arg.value);
            }
        });


    </script>

</@layout.myLayout>