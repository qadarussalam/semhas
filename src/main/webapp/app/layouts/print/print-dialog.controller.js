(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('PrintDialogController', PrintDialogController);

        PrintDialogController.$inject = ['$timeout', '$rootScope', '$scope', '$sce', '$uibModalInstance', 'template', 'title'];

    function PrintDialogController ($timeout, $rootScope, $scope, $sce, $uibModalInstance, template, title) {
        var vm = this;

        vm.report = $sce.trustAsHtml(template);
        
        $timeout(function () {
            $rootScope.$broadcast('savePdfEvent', {
                activePdfSaveId: 'report',
                activePdfSaveName: title
            });

            vm.report = '';
            
            $timeout(function () {
                $uibModalInstance.dismiss('cancel');
            }, 500);
        });
    }
})();