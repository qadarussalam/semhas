(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('DosenDetailController', DosenDetailController);

    DosenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Dosen'];

    function DosenDetailController($scope, $rootScope, $stateParams, previousState, entity, Dosen) {
        var vm = this;

        vm.dosen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('semhasApp:dosenUpdate', function(event, result) {
            vm.dosen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
