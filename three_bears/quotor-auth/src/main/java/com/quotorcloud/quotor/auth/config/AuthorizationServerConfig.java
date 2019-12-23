/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quotorcloud.quotor.auth.config;

import com.quotorcloud.quotor.common.core.constant.SecurityConstants;
import com.quotorcloud.quotor.common.security.service.QuotorClientDetailsService;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.component.QuotorWebResponseExceptionTranslator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lengleng
 * @date 2019/2/1
 * 认证服务器配置
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final DataSource dataSource;

	private final UserDetailsService userDetailsService;

	private final AuthenticationManager authenticationManager;

	private final RedisConnectionFactory redisConnectionFactory;


	/**
	 * 注册clients到授权服务器，这里是注册到JDBC中，且配置了scopes,authorities等信息
	 */
	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		QuotorClientDetailsService clientDetailsService = new QuotorClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	/**
	 * 当授权服务和资源服务不在一个应用程序的时候，资源服务可以把传递来的access_token递交给授权服务的/oauth/check_token进行验证，而资源服务自己无需去连接数据库验证access_token，这时就用到了RemoteTokenServices。
	 *
	 * loadAuthentication方法，设置head表头Authorization 存储clientId和clientSecret信息，请求参数包含access_token字符串，向AuthServer的CheckTokenEndpoint (/oauth/check_token)发送请求，返回验证结果map（包含clientId,grantType,scope,username等信息），拼接成OAuth2Authentication。
	 *
	 * 重要！！！
	 *
	 * AuthServer需要配置checkTokenAcess，否则默认为“denyAll()”，请求访问/oauth/check_token会提示没权限。
	 *
	 **/
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
			.allowFormAuthenticationForClients()
			.checkTokenAccess("permitAll()");
	}

	/**
	 * 可以设置tokenStore,tokenGranter,authenticationManager,requestFactory等
	 * 接口使用什么继承类，但一般沿用默认的就好了
	 * 如果使用的是密码方式授权，则必须设置authenticationManager
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
			.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
			//采用redis进行存储
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancer())
			.userDetailsService(userDetailsService)
			//切换成password授权
			.authenticationManager(authenticationManager)
			.reuseRefreshTokens(false)
			.exceptionTranslator(new QuotorWebResponseExceptionTranslator());
	}

	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstants.PROJECT_PREFIX + SecurityConstants.OAUTH_PREFIX);
		return tokenStore;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>();
			QuotorUser quotorUser = (QuotorUser) authentication.getUserAuthentication().getPrincipal();
			additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PROJECT_LICENSE);
			additionalInfo.put(SecurityConstants.DETAILS_USER_ID, quotorUser.getId());
			additionalInfo.put(SecurityConstants.DETAILS_USERNAME, quotorUser.getUsername());
			additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID, quotorUser.getDeptId());
			additionalInfo.put(SecurityConstants.DETAILS_DEPT_NAME, quotorUser.getDeptName());
			additionalInfo.put(SecurityConstants.DETAILS_NAME, quotorUser.getName());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
}
