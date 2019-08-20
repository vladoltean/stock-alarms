<#import "/spring.ftl" as spring />

<html>
<link href="../css/custom.css" rel="stylesheet" id="bootstrap-css">
<link href="../css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
<link href="../css/bootstrap-grid.css" rel="stylesheet" id="bootstrap-css">
<link href="../css/bootstrap-reboot.css" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css">

<script src="https://code.jquery.com/jquery-3.4.1.slim.js" integrity="sha256-BTlTdQO9/fascB1drekrDVkaKd9PkwBymMlHOiG+qLI="
        crossorigin="anonymous"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap.bundle.js"></script>

<body>

<main role="main" class="container">

    <#if currentUser??>
        You are logged in as ${currentUser.username}
    </#if>

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
                    <td>${stock.price!'N/A'}</td>
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


    <form id="logoutForm" method="POST" action="<@spring.url "/logout"/>">
        <#--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        <button type="submit" value="Log Out">Log Out</button>
    </form>
</main>
</body>

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
</script>

</html>