<#import "/spring.ftl" as spring />
<#import "layout.ftl" as layout>
<#import "util-macros.ftl" as util>

<@layout.myLayout>

    <style>
        .inactive {
            color: gray;
        }
    </style>

    <@util.alarmSaving></@util.alarmSaving>

    <h3>Active Alarms: </h3>
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Symbol</th>
            <th scope="col">Initial Price</th>
            <th scope="col">Current Price</th>
            <th scope="col">Variance</th>
            <th scope="col">Target variance</th>
            <th scope="col">Target price</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <#list alarms as alarm>
            <tr>
                <th scope="row">${alarm.stock.symbol!'N/A'}</th>
                <td>$${alarm.referencePrice!'N/A'}</td>
                <td>$${alarm.stock.price!'N/A'}</td>
                <td>${alarm.variance!'N/A'}%</td>
                <td>${alarm.rule!'N/A'}%</td>
                <td>$${alarm.alarmPrice!'N/A'}</td>
                <td>
                    <button type="button" class="btn btn-info" data-toggle="modal"
                            data-target="#editAlarmModal" data-symbol="${alarm.stock.symbol}" data-initial-price="${alarm.referencePrice}"
                            data-current-price="${alarm.stock.price}" data-rule="${alarm.rule}" data-variance="${alarm.variance}"
                            data-id="${alarm.id}">
                        Edit
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                            data-target="#deleteAlarmModal" data-symbol="${alarm.stock.symbol}" data-initial-price="${alarm.referencePrice}"
                            data-current-price="${alarm.stock.price}" data-rule="${alarm.rule}" data-variance="${alarm.variance}"
                            data-id="${alarm.id}">
                        X
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <#if inactiveAlarms?size != 0>
        <h4>Inactive Alarms: </h4>
        <table class="table inactive">
            <thead class="thead-light">
            <tr>
                <th scope="col">Symbol</th>
                <th scope="col">Initial Price</th>
                <th scope="col">Current Price</th>
                <th scope="col">Variance</th>
                <th scope="col">Target variance</th>
                <th scope="col">Target price</th>
                <th scope="col">Triggered at</th>

            </tr>
            </thead>
            <tbody>
            <#list inactiveAlarms as alarm>
                <tr>
                    <th scope="row">${alarm.stock.symbol!'N/A'}</th>
                    <td>$${alarm.referencePrice!'N/A'}</td>
                    <td>$${alarm.stock.price!'N/A'}</td>
                    <td>${alarm.variance!'N/A'}%</td>
                    <td>${alarm.rule!'N/A'}%</td>
                    <td>$${alarm.alarmPrice!'N/A'}</td>
                    <td>${alarm.triggeredAt!'N/A'}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
    <!-- Modal -->
    <div class="modal fade" id="editAlarmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Edit alarm</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="add-alarm-form" method="POST" action="alarms">
                        <div>
                            <h4>Symbol</h4>
                            <p id="stockSymbolInfo">GHR</p>
                        </div>
                        <div>
                            <h4>Initial price</h4>
                            <p id="initialPriceInfo">20.3</p>
                        </div>
                        <div>
                            <h4>Current price</h4>
                            <p id="currentPriceInfo">22.3</p>
                        </div>
                        <div>
                            <h4>Variance</h4>
                            <p id="varianceInfo">22.3</p>
                        </div>

                        <h4>Target Variance</h4>
                        <input id="identifier" type="number" name="id" value="5" hidden>
                        <input id="stockSymbol" type="text" name="stockSymbol" value="GSG" hidden>
                        <input id="rule" class="form-control" type="text" placeholder="Rule" name="rule">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" form="add-alarm-form" class="btn btn-primary">Save Alarm</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="deleteAlarmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Edit alarm</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <h4 class="text-danger">Are you sure you want to delete the following alarm?</h4>
                    <br>
                    <form id="add-alarm-form">
                        <div>
                            <h5>Symbol</h5>
                            <p id="stockSymbolInfo">GHR</p>
                        </div>
                        <div>
                            <h5>Initial price</h5>
                            <p id="initialPriceInfo">20.3</p>
                        </div>
                        <div>
                            <h4>Current price</h4>
                            <p id="currentPriceInfo">22.3</p>
                        </div>
                        <div>
                            <h5>Variance</h5>
                            <p id="varianceInfo">22.3</p>
                        </div>
                        <div>
                            <h5>Target Variance</h5>
                            <p id="rule">22.3</p>
                        </div>

                        <input id="identifier" type="number" name="id" value="5" hidden>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" id="delete-alarm" form="add-alarm-form" class="btn btn-danger">Delete Alarm</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        $('#editAlarmModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var symbol = button.data('symbol'); // Extract info from data-* attributes
            var initialPrice = button.data('initial-price');
            var currentPrice = button.data('current-price');
            var variance = button.data('variance');
            var rule = button.data('rule');
            var id = button.data('id');

            var modal = $(this);

            modal.find('.modal-title').text('Edit alarm for ' + symbol);
            modal.find('#identifier').val(id);
            modal.find('#stockSymbol').val(symbol);
            modal.find('#rule').val(rule);
            modal.find('#stockSymbolInfo').text(symbol);
            modal.find('#initialPriceInfo').text(initialPrice);
            modal.find('#currentPriceInfo').text(currentPrice);
            modal.find('#varianceInfo').text(variance);
        })
        $('#deleteAlarmModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var symbol = button.data('symbol'); // Extract info from data-* attributes
            var initialPrice = button.data('initial-price');
            var currentPrice = button.data('current-price');
            var variance = button.data('variance');
            var rule = button.data('rule');
            var id = button.data('id');

            var modal = $(this);

            modal.find('.modal-title').text('Delete alarm for ' + symbol);
            modal.find('#identifier').val(id);
            modal.find('#rule').text(rule);
            modal.find('#stockSymbolInfo').text(symbol);
            modal.find('#initialPriceInfo').text(initialPrice);
            modal.find('#currentPriceInfo').text(currentPrice);
            modal.find('#varianceInfo').text(variance);
            modal.find('#delete-alarm').data('id', id);
        });
        $('#delete-alarm').click(function(){
            // var button = $(event.relatedTarget);
            var id = $(this).data('id');

            console.log("id", id);

            $.ajax({
                success: function(){
                    window.location.reload();
                },
                error: function(err){
                    console.log("Error deleting alarm!", err);
                },
                processData: false,
                type: 'DELETE',
                url: '/alarms/' + id
            });
        })
    </script>

</@layout.myLayout>
