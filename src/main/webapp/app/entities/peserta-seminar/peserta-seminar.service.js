(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('PesertaSeminar', PesertaSeminar);

    PesertaSeminar.$inject = ['$resource'];

    function PesertaSeminar ($resource) {
        var resourceUrl =  'api/peserta-seminars/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'printKps': {
                url: 'api/mahasiswas/:id/kps/printable',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        var result = {
                            html: data
                        }

                        return result;
                    }
                    return data;
                }
            }
        });
    }
})();
