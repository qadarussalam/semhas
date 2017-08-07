(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JadwalSeminarController', JadwalSeminarController);

    JadwalSeminarController.$inject = ['$state', 'JadwalSeminar', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function JadwalSeminarController($state, JadwalSeminar, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.setActive = setActive;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {
            JadwalSeminar.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.jadwalSeminars = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function setActive (jadwalSeminar, isActivated) {
            jadwalSeminar.tersedia = isActivated;
            JadwalSeminar.update(jadwalSeminar, function () {
                loadAll();
            });
        }
    }
})();
