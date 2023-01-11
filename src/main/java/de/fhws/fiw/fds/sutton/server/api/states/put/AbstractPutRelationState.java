package de.fhws.fiw.fds.sutton.server.api.states.put;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractPutRelationState extends the {@link AbstractPutState} with the required functionality to
 * update an existing sub-resource associated with a specific primary resource. The functionalities provided by this class
 * can be used also to relate an existing sub-resource to an existing primary resource.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 *  * {@link AbstractPutRelationState.AbstractPutRelationStateBuilder}</p>
 *
 * @see AbstractPutState
 * */
public abstract class AbstractPutRelationState<T extends AbstractModel> extends AbstractPutState<T>
{
	/**
	 * The id {@link Long} of the primary resource
	 * */
	protected long primaryId;

	public AbstractPutRelationState( final AbstractPutRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractPutRelationStateBuilder<T extends AbstractModel>
		extends AbstractPutStateBuilder<T>
	{
		protected long parentId;

		public AbstractPutRelationStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
