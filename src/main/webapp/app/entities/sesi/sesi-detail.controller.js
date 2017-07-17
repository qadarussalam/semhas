(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SesiDetailController', SesiDetailController);

    SesiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sesi', 'JadwalSeminar'];

    function SesiDetailController($scope, $rootScope, $stateParams, previousState, entity, Sesi, JadwalSeminar) {
        var vm = this;

        vm.sesi = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('semhasApp:sesiUpdate', function(event, result) {
            vm.sesi = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
