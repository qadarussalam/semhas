(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('MySeminarController', MySeminarController);

    MySeminarController.$inject = ['$scope', '$state', 'Seminar', 'Mahasiswa'];

    function MySeminarController($scope, $state, Seminar, Mahasiswa) {
        var vm = this;

        var token = localStorage['jhi-authenticationToken'];
        var data = jwt_decode(token);
        var mahasiswaId = data['semhas.mhsw'];

        vm.mySeminar = null;

        Mahasiswa.getSeminar({id: mahasiswaId}, function(data) {
            vm.mySeminar = data;

            Mahasiswa.get({id: vm.mySeminar.mahasiswaId}, function(data2) {
                vm.mahasiswa = data2;
            })
        })
    }
})();
