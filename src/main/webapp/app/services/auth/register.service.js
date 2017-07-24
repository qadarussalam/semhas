(function () {
    'use strict';

    angular
        .module('semhasApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        var service = $resource('api/register', {}, {
            'registerMahasiswa': {
                url: 'api/register/mahasiswa',
                method: 'POST'
            }
        });

        return service;
    }
})();
