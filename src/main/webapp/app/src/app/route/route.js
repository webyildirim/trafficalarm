angular.module('ngBoilerplate.route', ['ui.router', 'ngResource', 'ngBoilerplate.account', 'hateoas', 'yaMap'])
.config(function($stateProvider) {
    $stateProvider.state('manageRoutes', {
            url:'/manage/routes?accountId',
            views: {
                'main': {
                    templateUrl:'blog/manage-blogs.tpl.html',
                    controller: 'ManageRouteCtrl'
                }
            },
            resolve: {
                account: function(accountService, $stateParams) {
                    return accountService.getAccountById($stateParams.accountId);
                },
                blogs: function(routeService, $stateParams) {
                    return routeService.getRoutesForAccount($stateParams.accountId);
                }
            },
            data : { pageTitle : "Rotalarım" }
    });
})
.factory('blogService', function($resource, $q) {
      var service = {};
      service.createRoute = function(accountId, blogData) {
        var Blog = $resource("/trafficalarm/rest/accounts/:paramAccountId/blogs");
        return Blog.save({paramAccountId: accountId}, blogData).$promise;
      };
      service.getAllRoutes = function() {
        var Blog = $resource("/trafficalarm/rest/blogs");
        return Blog.get().$promise.then(function(data) {
            return data.blogs;
        });
      };
      service.getRoutesForAccount = function(accountId) {
          var deferred = $q.defer();
          var Account = $resource("/trafficalarm/rest/accounts/:paramAccountId");
          Account.get({paramAccountId:accountId}, function(account) {
              var Blog = account.resource('blogs');
              Blog.get(null,
                  function(data) {
                    deferred.resolve(data.blogs);
                  },
                  function() {
                    deferred.reject();
                  }
              );
          });
          return deferred.promise;
      };
      return service;
})
.controller("ManageRouteCtrl", function($scope, blogs, account, blogService, $state) {
    $scope.name = account.name;
    $scope.blogs = blogs;
    $scope.createBlog = function(blogName) {
        blogService.createBlog(account.rid, {
            title : blogName
        }).then(function() {
            $state.go("manageRoutes", { accountId : account.rid }, { reload : true });
        });
    };
});