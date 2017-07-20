(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JurusanDetailController', JurusanDetailController);

    JurusanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Jurusan', 'User', 'Mahasiswa'];

    function JurusanDetailController($scope, $rootScope, $stateParams, previousState, entity, Jurusan, User, Mahasiswa) {
        var vm = this;

        vm.jurusan = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('semhasApp:jurusanUpdate', function(event, result) {
            vm.jurusan = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
