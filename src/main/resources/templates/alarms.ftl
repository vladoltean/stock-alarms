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

        </tr>
        </thead>
        <tbody>
        <#list alarms as alarm>
            <tr>
                <th scope="row">${alarm.stock.symbol!'N/A'}</th>
                <td>$${alarm.referencePrice!'N/A'}</td>
                <td>$${alarm.stock.price!'N/A'}</td>
                <td>${alarm.variance!'N/A'}</td>
                <td>${alarm.rule!'N/A'}</td>
                <td>$${alarm.alarmPrice!'N/A'}</td>
                <td>
                    <button type="button" class="btn btn-info" data-toggle="modal"
                            data-target="#editAlarmModal" data-symbol="${alarm.stock.symbol}" data-initial-price="${alarm.referencePrice}"
                                data-current-price="${alarm.stock.price}" data-rule="${alarm.rule}" data-variance="${alarm.variance}"
                                data-id="${alarm.id}">
                        Edit
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

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
                        <#--                        TODO: Add tooltip and validation to rule-->
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
                        <input id="rule" class="form-control" type="text" placeholder="Rule" name="rule" value="+20">
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
        $('#editAlarmModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var symbol = button.data('symbol'); // Extract info from data-* attributes
            var initialPrice = button.data('initial-price');
            var currentPrice = button.data('current-price');
            var variance = button.data('variance');
            var rule = button.data('rule');
            var id = button.data('id');

            var modal = $(this);

            modal.find('.modal-title').text('Add alarm for ' + symbol);
            modal.find('#identifier').val(id);
            modal.find('#stockSymbol').val(symbol);
            modal.find('#rule').val(rule);
            modal.find('#stockSymbolInfo').text(symbol);
            modal.find('#initialPriceInfo').text(initialPrice);
            modal.find('#currentPriceInfo').text(currentPrice);
            modal.find('#varianceInfo').text(variance);
        })
    </script>

</@layout.myLayout>
