(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('MySeminarController', MySeminarController);

    MySeminarController.$inject = ['$scope', '$state', '$localStorage', '$uibModal', 'Seminar', 'Mahasiswa'];

    function MySeminarController($scope, $state, $localStorage, $uibModal, Seminar, Mahasiswa) {
        var vm = this;

        vm.openDialogPrint = openDialogPrint;

        var token = $localStorage.authenticationToken;
        var data = jwt_decode(token);
        var mahasiswaId = data['semhas.mhsw'];

        vm.mySeminar = null;

        Mahasiswa.getSeminar({id: mahasiswaId}, function(data) {
            vm.mySeminar = data;

            Mahasiswa.get({id: vm.mySeminar.mahasiswaId}, function(data2) {
                vm.mahasiswa = data2;
            });
        });
        
        function openDialogPrint() {
            Seminar.printPesertaSeminar({
                idseminar: vm.mySeminar.id
            }, function(data) {
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/layouts/print/print-dialog.html',
                    controller: 'PrintDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        template: function () {
                            return data.html;
                        },
                        title: function() {
                            return 'peserta-seminar.pdf'
                        }
                    }
                });
            });
        }
    }
})();
