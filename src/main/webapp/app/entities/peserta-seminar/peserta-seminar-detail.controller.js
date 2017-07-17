(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('PesertaSeminarDetailController', PesertaSeminarDetailController);

    PesertaSeminarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PesertaSeminar', 'Mahasiswa', 'Seminar'];

    function PesertaSeminarDetailController($scope, $rootScope, $stateParams, previousState, entity, PesertaSeminar, Mahasiswa, Seminar) {
        var vm = this;

        vm.pesertaSeminar = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('semhasApp:pesertaSeminarUpdate', function(event, result) {
            vm.pesertaSeminar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
