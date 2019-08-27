<#import "layout-not-authorized.ftl" as layout>

<@layout.unauthorizedLayout>

    <div class="card card-login mx-auto mt-5">
        <div class="card-header">Login</div>
        <div class="card-body">
            <form id="login-form" method="POST" action="login">
                <div class="form-group">
                    <div class="form-label-group">
                        <input type="text" id="inputUsername" class="form-control" placeholder="Username" required="required"
                               autofocus="autofocus" name="username">
                        <label for="inputUsername">Username</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-label-group">
                        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="required" name="password" >
                        <label for="inputPassword">Password</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>
            <div class="text-center">
                <a class="d-block small mt-3" href="/register">Register an Account</a>
            </div>
        </div>
    </div>

</@layout.unauthorizedLayout>
