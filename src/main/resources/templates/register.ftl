<#import "/spring.ftl" as spring />

<#import "layout-not-authorized.ftl" as layout>

<@layout.unauthorizedLayout>

    <div class="card card-login mx-auto mt-5">
        <div class="card-header">Login</div>
        <div class="card-body">
            <form id="login-form" method="POST" action="register">
                <div class="form-group">
                    <div class="form-label-group">
                        <@spring.formInput 'person.username' 'class="form-control" placeholder="Email"
                               required="required" autofocus="autofocus"' 'email'/>
                        <@spring.showErrors "<br>", "text-danger"/>
                        <label for="username">Email</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-label-group">
                        <@spring.formInput 'person.firstName' 'class="form-control" placeholder="First name" required="required"' />
                        <@spring.showErrors "<br>", "text-danger"/>
                        <label for="firstName">First name</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-label-group">
                        <@spring.formInput 'person.lastName' 'class="form-control" placeholder="Last name" required="required"'/>
                        <@spring.showErrors "<br>", "text-danger"/>
                        <label for="lastName">Last Name</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-label-group">
                        <@spring.formInput 'person.password' 'class="form-control" placeholder="Password" required="required"' 'password'/>
                        <@spring.showErrors "<br>", "text-danger"/>
                        <label for="password">Password</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-label-group">
                        <@spring.formInput 'person.passwordConfirm' 'class="form-control" placeholder="Password Confirm"
                        required="required"' 'password'/>
                        <@spring.showErrors "<br>", "text-danger"/>
                        <label for="passwordConfirm">Password Confirm</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Sign up</button>
            </form>
            <div class="text-center">
                <a class="d-block small mt-3" href="/login">Go to Log In page</a>
            </div>
        </div>
    </div>

</@layout.unauthorizedLayout>
