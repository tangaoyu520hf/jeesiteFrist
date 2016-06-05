package com.thinkgem.jeesite.tools;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLNamespaceHandlerTest extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("datasource", new DatasourceBeanDefinitionParser());
	}
	
	
	private class DatasourceBeanDefinitionParser implements BeanDefinitionParser{
		@Override
		public BeanDefinition parse(Element element, ParserContext parserContext) {
			String attribute = element.getAttribute("id");
			NodeList childNodes = element.getChildNodes();
			return null;
		}
	}

}
