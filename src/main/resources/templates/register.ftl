<#import "/spring.ftl" as spring />

<#if errors??>

<#--    ${errors.toString()}-->
    EROORS
</#if>

<form action="/register" method="post">
    Username:<br>
    <@spring.formInput "person.username"/>
    <@spring.showErrors "<br>"/>
    First name:<br>
    <@spring.formInput "person.firstName"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Last name:<br>
    <@spring.formInput "person.lastName"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <br><br>
    Password:<br>
    <@spring.formInput "person.password", "", "password"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Password:<br>
    <@spring.formInput "person.passwordConfirm", "", "password"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Submit">
</form>