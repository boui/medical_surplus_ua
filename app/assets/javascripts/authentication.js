'use strict';
angular.module("surplus.auth").controller("AuthController", function ($scope, $http) {
    $scope.credentials = {
        login: undefined, password: undefined
    };
    $scope.login = function (credentials) {
        console.log(credentials)
        return $http.post("/auth", credentials)
            .success(function (response) {
                if (angular.isDefined(response))  $http.defaults.headers.common["X-AUTH-TOKEN"] = response.token;
            })
            .error(function (data, status) {
                alert("error occured")
            });
    }
});