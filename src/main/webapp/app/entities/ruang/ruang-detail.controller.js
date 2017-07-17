(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('RuangDetailController', RuangDetailController);

    RuangDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ruang', 'JadwalSeminar'];

    function RuangDetailController($scope, $rootScope, $stateParams, previousState, entity, Ruang, JadwalSeminar) {
        var vm = this;

        vm.ruang = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('semhasApp:ruangUpdate', function(event, result) {
            vm.ruang = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
